package com.usargis.usargisapi.util;

public abstract class Constant {
    //### ROLES ###
    public static final String MEMBER_ROLE = "MEMBER";
    public static final String LEADER_ROLE = "LEADER";
    public static final String ADMIN_ROLE = "ADMIN";
    //### PATH ###
    public static final Object SLASH = "/";
    public static final Object REDIRECT = "redirect:";
    public static final String SLASH_ID_PATH = "/{id}";
    public static final String SLASH_STRING_PATH = "/{string}";
    public static final String SLASH_NAME_PATH = "/{name}";
    public static final String SLASH_USERNAME_PATH = "/{username}";
    public static final String V1_PATH = "/v1";
    public static final String API_PATH = "/api";
    public static final String USARGIS_PATH = "/usar-gis";
    public static final String AVAILABILITIES_PATH = "/availabilities";
    public static final String EVENTS_PATH = "/events";
    public static final String GROUPS_PATH = "/groups";
    public static final String INSCRIPTIONS_PATH = "/inscriptions";
    public static final String MISSIONS_PATH = "/missions";
    public static final String NOTIFICATIONS_PATH = "/notifications";
    public static final String NOTIFICATION_MESSAGES_PATH = "/notification-messages";
    public static final String TEAMS_PATH = "/teams";
    public static final String TEAM_MEMBERS_PATH = "/team-members";
    public static final String USERS_PATH = "/users";
    //### STATUS ###
    public static final String STATUS_PENDING_CODE = "PE";
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_PLANNED_CODE = "PL";
    public static final String STATUS_PLANNED = "planned";
    public static final String STATUS_ONFOCUS_CODE = "OF";
    public static final String STATUS_ONFOCUS = "onfocus";
    public static final String STATUS_TEAMENGAGEMENT_CODE = "TE";
    public static final String STATUS_TEAMENGAGEMENT = "teamEngagement";
    public static final String STATUS_ONGOING_CODE = "OG";
    public static final String STATUS_ONGOING = "ongoing";
    public static final String STATUS_FINISHED_CODE = "FI";
    public static final String STATUS_FINISHED = "finished";
    public static final String STATUS_SENT_CODE = "ST";
    public static final String STATUS_SENT = "sent";
    public static final String STATUS_CANCELLED_CODE = "CA";
    public static final String STATUS_CANCELLED = "cancelled";
    public static final String STATUS_WAITING_FOR_VALIDATION_CODE = "WFV";
    public static final String STATUS_WAITING_FOR_VALIDATION = "waitingForValidation";
    public static final String STATUS_VALIDATED_CODE = "VA";
    public static final String STATUS_VALIDATED = "validated";
    //### NOTIFICATION MESSAGE SENDING MODE ###
    public static final String NMSM_APP_CODE = "A";
    public static final String NMSM_APP = "APP";
    public static final String NMSM_MAIL_CODE = "M";
    public static final String NMSM_MAIL = "MAIL";
    public static final String NMSM_PHONE_CODE = "P";
    public static final String NMSM_PHONE = "PHONE";
    //### NOTIFICATION MESSAGE SENDING TYPE ###
    public static final String NMCT_HTML_CODE = "H";
    public static final String NMCT_HTML = "HTML";
    public static final String NMCT_TEXT_CODE = "T";
    public static final String NMCT_TEXT = "TEXT";

    private Constant() {
    }


}
