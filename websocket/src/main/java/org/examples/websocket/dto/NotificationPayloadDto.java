package org.examples.websocket.dto;

import java.util.Objects;

public record NotificationPayloadDto (
        String title,
        String body,
        String icon) {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (NotificationPayloadDto) obj;
        return Objects.equals(this.title, that.title) &&
                Objects.equals(this.body, that.body) &&
                Objects.equals(this.icon, that.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, icon);
    }

    @Override
    public String toString() {
        return "NotificationPayloadDto[" +
                "title=" + title + ", " +
                "body=" + body + ", " +
                "icon=" + icon + ']';
    }


}
