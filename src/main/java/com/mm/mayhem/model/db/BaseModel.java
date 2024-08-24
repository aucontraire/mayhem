package com.mm.mayhem.model.db;

import java.time.ZonedDateTime;

public abstract class BaseModel {

    protected ZonedDateTime created;

    protected ZonedDateTime updated;

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }
}
