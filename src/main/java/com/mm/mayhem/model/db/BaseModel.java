package com.mm.mayhem.model.db;

import java.time.LocalDateTime;

public abstract class BaseModel {

    protected LocalDateTime created;

    protected LocalDateTime updated;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
