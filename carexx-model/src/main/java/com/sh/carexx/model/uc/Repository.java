package com.sh.carexx.model.uc;

public class Repository {

    private Long id;

    private String diseaseName; //'病种'

    private String instructions;//'说明'

    private String year;//'公示年份'

    private String standard;//'标准'

    private Byte enableStatus; //启用状态 1已启用

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Byte getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Byte enableStatus) {
        this.enableStatus = enableStatus;
    }
}