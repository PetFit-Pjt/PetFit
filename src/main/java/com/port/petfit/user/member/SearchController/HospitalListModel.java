package com.port.petfit.user.member.SearchController;

import java.util.List;

import com.port.petfit.user.member.account.Hospital;

public class HospitalListModel {
    private List<Hospital> hospitals;

    public HospitalListModel(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }
}
