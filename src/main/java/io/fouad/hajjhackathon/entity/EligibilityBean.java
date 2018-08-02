package io.fouad.hajjhackathon.entity;

public class EligibilityBean
{
    private boolean eligible;

    public EligibilityBean(boolean eligible) {
        this.eligible = eligible;
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }
}