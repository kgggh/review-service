package com.app.review.model.entity.common;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    @PrePersist
    public void onPrePersist() {
        this.createdDateTime = LocalDateTime.now().withNano(0);
        this.modifiedDateTime = this.createdDateTime;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.modifiedDateTime = LocalDateTime.now().withNano(0);
    }
}