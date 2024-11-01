package com.chxt.fantasticmonkey.domain.tennis;

import com.chxt.fantasticmonkey.model.tennis.DayTennisCourt;
import com.chxt.fantasticmonkey.model.tennis.TennisCourt;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.swing.Java2DRenderer;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;

public class TennisReportGenerator {

    private final List<TennisCourt> tennisCourts;

    private final List<TennisCourt> diff;

    private final Template template;


    @SneakyThrows
    public TennisReportGenerator(Configuration configuration, List<TennisCourt> tennisCourts, List<TennisCourt> diff) {
        this.template = configuration.getTemplate("TennisReport.ftl");
        this.tennisCourts = tennisCourts;
        this.diff = diff;
    }

    @SneakyThrows
    public byte[] get() {
        // 使用模板生成html
        HashMap<String, List<DayTennisCourt>> data = new HashMap<>();
        List<DayTennisCourt> dayTennisCourts = DayTennisCourt.getList(tennisCourts);
        data.put("dayTennisCourtList", dayTennisCourts);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        template.process(data, writer);

        // 生成图片
        ByteArrayInputStream bin = new ByteArrayInputStream(os.toByteArray());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(bin);
        Java2DRenderer renderer = new Java2DRenderer(document,1400,1000);
        SharedContext sharedContext = renderer.getSharedContext();
        sharedContext.setDotsPerPixel(2);

        BufferedImage img = renderer.getImage();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", bos);
        return bos.toByteArray();
    }


}
