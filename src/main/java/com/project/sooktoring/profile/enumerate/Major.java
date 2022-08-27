package com.project.sooktoring.profile.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.project.sooktoring.common.exception.CustomException;

import static com.project.sooktoring.common.exception.ErrorCode.*;

public enum Major {
    //문과대학
    KOREA_LITERATURE_DEPT("한국어문학부"),
    HISTORY_CULTURE_DEPT("역사문화학과"),
    FRENCH_CULTURE_DEPT("프랑스언어·문화학과"),
    CHINESE_LITERATURE_DEPT("중어중문학부"),
    GERMAN_CULTURE_DEPT("독일언어·문화학과"),
    JAPANESE_DEPT("일본학과"),
    LITERATURE_INFORMATION_DEPT("문헌정보학과"),
    CULTURE_TOURISM_MAJOR("문화관광학전공"),
    LE_CORDON_BLUE_MAJOR("르꼬르동블루외식경영전공"),
    EDUC_DEPT("교육학부"),

    //이과대학
    CHEMISTRY_DEPT("화학과"),
    LIFE_SYSTEMS_DEPT("생명시스템학부"),
    MATHEMATICS_DEPT("수학과"),
    STATISTICS_DEPT("통계학과"),
    PHYSICAL_EDUC_DEPT ("체육교육과"),
    DANCE_DEPT("무용과"),

    //공과대학
    CHEMICAL_BIOLOGY_DEPT("화공생명학부"),
    AI_ENGINEERING_DEPT("인공지능공학부"),
    INTELLIGENT_ELECTRONIC_SYSTEMS_MAJOR("지능형전자시스템전공"),
    NEW_MATERIAL_PHYSICS_MAJOR("신소재물리전공"),
    COMPUTER_SCIENCE_MAJOR("컴퓨터과학전공"),
    DATA_SCIENCE_MAJOR("데이터사이언스전공"),
    MECHANICAL_SYSTEMS_DEPT("기계시스템학부"),
    BASIC_ENGINEERING_DEPT("기초공학부"),

    //생활과학대학
    FAMILY_RESOURCE_MANAGEMENT_DEPT("가족자원경영학과"),
    CHILD_WELFARE_DEPT("아동복지학부"),
    CLOTHING_DEPT("의류학과"),
    FOOD_NUTRITION_DEPT("식품영양학과"),

    //사회과학대학
    POLITICAL_DIPLOMACY_DEPT("정치외교학과"),
    PUBLIC_ADMINISTRATION_DEPT("행정학과"),
    ADVERTISING_DEPT("홍보광고학과"),
    CONSUMER_ECONOMICS_DEPT("소비자경제학과"),
    SOCIAL_PSYCHOLOGY_DEPT("사회심리학과"),

    //법과대학
    LAW_DEPT("법학부"),

    //경상대학
    ECONOMICS_DEPT("경제학부"),
    BUSINESS_ADMINISTRATION_DEPT("경영학부"),

    //음악대학
    PIANO_DEPT("피아노과"),
    ORCHESTRA_DEPT("관현악과"),
    VOCAL_MUSIC_DEPT("성악과"),
    COMPOSITION_DEPT("작곡과"),

    //약학대학
    PHARMACY_DEPT("약학부"),

    //미술대학
    VISUAL_VIDEO_DESIGN_DEPT("시각·영상 디자인과"),
    INDUSTRIAL_DESIGN_DEPT("산업디자인과"),
    ENVIRONMENTAL_DESIGN_DEPT("환경디자인과"),
    CRAFT_DEPT("공예과"),
    PAINTING_DEPT("회화과"),

    //기초교양대학
    BASIC_CULTURE_DEPT("기초교양학부"),
    CONVERGENCE_CONNECTION_DEPT("융합학부/연계전공"),

    //글로벌서비스학부
    GLOBAL_COOPERATION_MAJOR("글로벌협력전공"),
    ENTREPRENEURSHIP_MAJOR("앙트러프러너십전공"),

    //영어영문학부
    ENGLISH_LITERATURE_MAJOR("영어영문학전공"),
    TESL_MAJOR("테슬(TESL)전공"),

    //미디어학부
    MEDIA_DEPT("미디어학부"),

    DEFAULT("");

    private final String name;

    Major(String name) {
        this.name = name;
    }

    //역직렬화
    @JsonCreator
    public static Major from(String name) {
        for (Major major : Major.values()) {
            if (major.getName().equals(name)) {
                return major;
            }
        }
        throw new CustomException(NOT_FOUND_MAJOR_CAT);
    }

    //직렬화
    @JsonValue
    public String getName() {
        return name;
    }
}
