<!DOCTYPE html>
<html>
<body id="tinymce" class="mce-content-body " data-id="content" contenteditable="true" spellcheck="false">
<table style="border-collapse: collapse; width: 100%;" border="1">
    <tbody>
    <tr>
        <td></td>
        <#list dayTennisCourtList as dayTennisCourt>
            <td>${dayTennisCourt.day}</td>
        </#list>
    </tr>

    <#list 7..20 as i>
        <tr>
            <td>${i}</td>
            <#list dayTennisCourtList as dayTennisCourt>
                <td>
                    <#if dayTennisCourt.outdoorMap??>
                        <#if dayTennisCourt.outdoorMap[i+""]??>
                            <span>${dayTennisCourt.outdoorMap[i+""]!""}</span>
                        </#if>
                    </#if>
                    <#if dayTennisCourt.indoorMap??>
                        <#if dayTennisCourt.indoorMap[i+""]??>
                            <span style="border: 1px dashed">${dayTennisCourt.indoorMap[i+""]!""}</span>
                        </#if>
                    </#if>
                </td>
            </#list>

        </tr>
    </#list>
    </tbody>
</table>
<p>
    Emoticons:
    <span>
        outdoor
    </span>
    <span style="border: 1px dashed">
        indoor
    </span>
</p>
</body>
</html>