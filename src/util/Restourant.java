package util;

/**
 * Created by Lumin on 2015-12-01.
 * 이용 가능한 식당의 목록을 가져옵니다.
 */
public enum Restourant {
    UniversityClub("11"), EmployeeRestourant("07"), NewDormitory("12"), Dormitory("08"), RawBuilding("06"), CharmMaru("03"), SensibleMaru("02");

    String code;

    Restourant(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
