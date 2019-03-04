package ink.laoliang.jyuncmsplatform.domain.response;

import java.util.List;

public class FilterConditions {

    private List<String> dateList;

    private List<String> typeList;

    public FilterConditions(List<String> dateList, List<String> typeList) {
        this.dateList = dateList;
        this.typeList = typeList;
    }

    public List<String> getDateList() {
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }
}
