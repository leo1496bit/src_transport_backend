package com.src.src_transport.model;

import java.util.List;
import java.util.Map;

public class EmailBrevoRequest {
    private List<String> to;
    private int templateId;
    private Map<String, Object> params;
    private List<Attachment> attachments;


    public static class Attachment {
        private String name;
        private String content;
        private String type;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }
    public List<String> getTo() { return to; }
    public void setTo(List<String>to) { this.to = to; }
    public int getTemplateId() { return templateId; }
    public void setTemplateId(int templateId) { this.templateId = templateId; }
    public Map<String, Object> getParams() { return params; }
    public List<Attachment> getAttachments() { return attachments; }
    public void setParams(Map<String, Object> params) { this.params = params; }
}
