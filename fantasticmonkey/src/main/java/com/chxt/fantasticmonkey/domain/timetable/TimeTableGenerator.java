package com.chxt.fantasticmonkey.domain.timetable;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.DayOfWeek;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.lang3.time.DateUtils;

import com.chxt.fantasticmonkey.enums.TimetableEnum;
import com.chxt.fantasticmonkey.model.timetable.TimeTableCell;
import com.chxt.fantasticmonkey.util.DateStandard;


import lombok.SneakyThrows;

public class TimeTableGenerator {

 
    private static final String[] TIMES = { "08:00", "09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00",
            "17:00", "18:00", "19:00", "20:00", "21:00", "22:00" };

    // 定义时间列的宽度
    private static final int TIME_COLUMN_WIDTH = 80;

    // 图片大小
    private static final int IMAGE_WIDTH = 35;
    private static final int IMAGE_HEIGHT = 35;

    // 定义表格右边框距离图像边际的距离
    private static final int RIGHT_MARGIN = 20;

    // 定义交替背景颜色
    private static final Color color1 = Color.decode("#FFFFFF");
    private static final Color color2 = Color.decode("#FFFFFF");

    // 图片大小
    private final int width = 1000;
    private final int height = 1000;

    // 今日标识
    private static final String TODAY_MARKER_BASE64 = "iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAAAXNSR0IArs4c6QAAAgVJREFUeF7tWtFtgzAQxUHskU7SRoDEFm1GySRhDCSMSidJBwHcngQ/BHqWjQuGl8/YmLvnd+8O+0Rw8J84uP+BNQBpmn6MQQzDsC6K4ntJcLMsO7dt+zZesyzL3OY91gAkSaLGBgghrraGjdckoJVS9/H/UkorH6weJmMAABiAEDiGBpAKN03zHgQBKfGTGtso8RLPKqWGrFNHUXTTzUJaIpgkCTn8uYSh/7EGgUGZSEpZc+9jAeh3/sEttLVxAiGKogvHBBaAOI7vQoinYmdrDk/Zo5TKq6q6/mWrDgAPIcTZB4fHNhILqqp6sQJgqtDxBYzDA0AbxZXKbAj4zAAAAAYgBKABEEHmwMQoC1CFdTqdvrZUD3Rd9zpVsTphgIsjL1swTY/MjBgAABwceoIBlgisHgJz5/acX0sdn68OwJwBHACcSnPPD+MAwPDiZLEsAAbM7ABHYYSA5d0eNKBHACIIETS7PkcW4FRa9/4faRBpcDoGOYahDthLHcDttOvx1esA1w5y6wOAtQshbodcj4MBYMDKpbBrinPrIwQQAggBo25yo8/ho12OUofo5lpjOVHsx2sp5cW2Tc5bABZplPS5VZZrkiRmsBpAkw7dLD3Ez9bb5cnOvks8/z1kuWlqhB4DdBfzcZ5WCPjomK7NAEAXqb3OAwP2urO6foEBukjtdd4PWjOVX5aRM1gAAAAASUVORK5CYII=";
    // 今日标识与星期文字之间的间距（像素）
    private static final int TODAY_MARKER_SPACING = 4;


    public byte[] renderTimetable(List<TimeTableCell> timeTableCells) {

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // 设置背景颜色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        int rows = TIMES.length + 1; // 加一行用于显示星期几
        int cols = DateStandard.DAY_EN.length;
        int cellWidth = (width - TIME_COLUMN_WIDTH - RIGHT_MARGIN) / cols; // 表格使用剩余的宽度
        int cellHeight = height / rows;

        // 绘制背景颜色交替的行（不包括最后一行）
        for (int i = 1; i < TIMES.length; i++) {
            if (i % 2 == 0) {
                g2d.setColor(color1);
            } else {
                g2d.setColor(color2);
            }
            g2d.fillRect(TIME_COLUMN_WIDTH, i * cellHeight, width - TIME_COLUMN_WIDTH - RIGHT_MARGIN, cellHeight);
        }

        // 设置内部线条颜色
        g2d.setColor(Color.decode("#CFD3DC"));

        // 绘制内部横线（从第二行开始，不包括最后一行）
        for (int i = 1; i < TIMES.length; i++) {
            g2d.drawLine(TIME_COLUMN_WIDTH, i * cellHeight, width - RIGHT_MARGIN, i * cellHeight);
        }

        // 绘制内部竖线（从时间列之后开始，不包括最后一列）
        for (int i = 0; i < cols; i++) {
            g2d.drawLine(TIME_COLUMN_WIDTH + i * cellWidth, cellHeight, TIME_COLUMN_WIDTH + i * cellWidth,
                    TIMES.length * cellHeight);
        }

        // 设置外边框颜色
        g2d.setColor(Color.decode("#363637"));

        // 绘制外边框
        g2d.drawRect(TIME_COLUMN_WIDTH, cellHeight, width - TIME_COLUMN_WIDTH - RIGHT_MARGIN,
                TIMES.length * cellHeight - cellHeight);

        // 设置星期字体为粗体并居中显示
        g2d.setFont(new Font("Default", Font.BOLD, 12));
        g2d.setColor(Color.BLACK);
        FontMetrics metrics = g2d.getFontMetrics();
        for (int i = 0; i < DateStandard.DAY_EN.length; i++) {
            String dayText = DateStandard.DAY_EN[i];
            int textWidth = metrics.stringWidth(dayText);
            int x = TIME_COLUMN_WIDTH + i * cellWidth + (cellWidth - textWidth) / 2;
            int y = (cellHeight + metrics.getAscent()) / 2; // 垂直居中
            g2d.drawString(dayText, x, y);
        }

        // 在绘制星期标题后，添加今日标识
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        int todayIndex = dayOfWeek.getValue() - 1; // 转换为0-6的索引

        // 绘制今日标识
        try {
            byte[] markerImageBytes = Base64.getDecoder().decode(TODAY_MARKER_BASE64);
            BufferedImage markerImage = ImageIO.read(new ByteArrayInputStream(markerImageBytes));
            int markerSize = 20; // 标识图片的大小
            int markerX = TIME_COLUMN_WIDTH + todayIndex * cellWidth + (cellWidth - markerSize) / 2;
            int markerY = cellHeight / 2 + metrics.getAscent() / 2 + TODAY_MARKER_SPACING;
            g2d.drawImage(markerImage, markerX, markerY, markerSize, markerSize, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 填充时间点
        g2d.setFont(new Font("Default", Font.PLAIN, 12)); // 恢复为普通字体
        for (int i = 0; i < TIMES.length; i++) {
            g2d.drawString(TIMES[i], TIME_COLUMN_WIDTH / 4, (i + 1) * cellHeight);
        }

        // 渲染图片
        Map<String, List<String>> renderedKeys = new HashMap<>();
        for (TimeTableCell timeTableCell : timeTableCells) {
            int dayIndex = timeTableCell.getDayOfWeek() - 1; // 将1-7转换为0-6索引
            int timeIndex = Arrays.asList(TIMES).indexOf(timeTableCell.getTime());

            if (dayIndex >= 0 && dayIndex < DateStandard.DAY_EN.length && timeIndex != -1) {
                String cellKey = dayIndex + "-" + timeIndex;
                renderedKeys.putIfAbsent(cellKey, new ArrayList<>());

                if (!renderedKeys.get(cellKey).contains(timeTableCell.getIconKey())) {
                    renderedKeys.get(cellKey).add(timeTableCell.getIconKey());
                }
            }
        }

        for (String cellKey : renderedKeys.keySet()) {
            String[] indices = cellKey.split("-");
            int dayIndex = Integer.parseInt(indices[0]);
            int timeIndex = Integer.parseInt(indices[1]);
            List<String> keys = renderedKeys.get(cellKey);

            int centerX = TIME_COLUMN_WIDTH + dayIndex * cellWidth + cellWidth / 2;
            int imageY = (timeIndex + 1) * cellHeight + (cellHeight - IMAGE_HEIGHT) / 2;

            int totalWidth = keys.size() * IMAGE_WIDTH;
            int startX = centerX - totalWidth / 2;

            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                TimeTableCell timeTableCell = timeTableCells.stream().filter(data -> data.getIconKey().equals(key)).findFirst()
                        .orElse(null);
                if (timeTableCell != null) {
                    byte[] imageBytes = Base64.getDecoder().decode(timeTableCell.getIconBase64());
                    try {
                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                        int imageX = startX + i * IMAGE_WIDTH;
                        g2d.drawImage(image, imageX, imageY, IMAGE_WIDTH, IMAGE_HEIGHT, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        g2d.dispose();

        // 将图像转换为字节数组
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SneakyThrows
    public static void main(String[] args) {

        List<TimeTableCell> timeTables = new ArrayList<>();
        timeTables.add(new TimeTableCell(DateUtils.parseDate("2024-11-10 11:00", "yyyy-MM-dd HH:mm"), TimetableEnum.HL_INDOOR));
        timeTables.add(new TimeTableCell(DateUtils.parseDate("2024-11-10 12:00", "yyyy-MM-dd HH:mm"), TimetableEnum.HL_INDOOR));
        timeTables.add(new TimeTableCell(DateUtils.parseDate("2024-11-11 11:00", "yyyy-MM-dd HH:mm"), TimetableEnum.HL_INDOOR));
        timeTables.add(new TimeTableCell(DateUtils.parseDate("2024-11-12 11:00", "yyyy-MM-dd HH:mm"), TimetableEnum.HL_INDOOR));    

        

        // 调用方法渲染时间表并获取字节数组
        byte[] imageBytes = new TimeTableGenerator().renderTimetable(timeTables);

        // 使用字节数组渲染JFrame
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new JLabel(new ImageIcon(image)));
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
