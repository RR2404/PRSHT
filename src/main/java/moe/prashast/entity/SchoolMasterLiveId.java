package moe.prashast.entity;

import java.io.Serializable;
import java.util.Objects;

public class SchoolMasterLiveId implements Serializable {

    private Integer schoolId;
    private Short yearId;

    public SchoolMasterLiveId() {}

    public SchoolMasterLiveId(Integer schoolId, Short yearId) {
        this.schoolId = schoolId;
        this.yearId = yearId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchoolMasterLiveId)) return false;
        SchoolMasterLiveId that = (SchoolMasterLiveId) o;
        return Objects.equals(schoolId, that.schoolId)
                && Objects.equals(yearId, that.yearId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolId, yearId);
    }
}
