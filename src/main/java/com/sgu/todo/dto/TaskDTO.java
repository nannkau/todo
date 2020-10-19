package com.sgu.todo.dto;

import org.springframework.web.multipart.MultipartFile;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class TaskDTO {
    private  MultipartFile[] parts;
    @NotEmpty(message = "Enter title")
    private String title;
    @NotNull(message = "Enter finish date")
    private Date finishDate;
    @NotEmpty(message = "chose privacy")
    private String privacy;
    @NotEmpty(message = "chose status")
    private String status;

    public MultipartFile[] getParts() {
        return parts;
    }

    public void setParts(MultipartFile[] parts) {
        this.parts = parts;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
