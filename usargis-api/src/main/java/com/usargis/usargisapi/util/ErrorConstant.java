package com.usargis.usargisapi.util;

public abstract class ErrorConstant {
    public static final String NO_AVAILABILITY_FOUND = "No availability found";
    public static final String NO_AVAILABILITY_FOUND_FOR_ID = "Availability not found for this id : {0}";
    public static final String NO_EVENT_FOUND = "No event found";
    public static final String NO_EVENT_FOUND_FOR_ID = "Event not found for this id : {0}";
    public static final String NO_GROUP_FOUND = "No group found";
    public static final String NO_GROUP_FOUND_FOR_ID = "Group not found for this id : {0}";
    public static final String NO_MISSION_FOUND = "No mission found";
    public static final String NO_MISSION_FOUND_FOR_ID = "Mission not found for this id : {0}";
    public static final String NO_NOTIFICATION_FOUND = "No notification found";
    public static final String NO_NOTIFICATION_FOUND_FOR_ID = "Notification not found for this id : {0}";
    public static final String NO_TEAM_FOUND = "No team found";
    public static final String NO_TEAM_FOUND_FOR_ID = "Team not found for this id : {0}";
    public static final String NO_USER_FOUND = "No user found";
    public static final String NO_USER_FOUND_FOR_USERNAME = "User not found for this username : {0}";
    public static final String NO_USER_FOUND_FOR_ID = "User not found for this id : {0}";
    public static final String NO_INSCRIPTION_FOUND = "No inscription found";
    public static final String NO_INSCRIPTION_FOUND_FOR_ID = "Inscription not found for this id : {0}";
    public static final String NO_NOTIFICATION_MESSAGE_FOUND = "No notification messages found";
    public static final String NO_NOTIFICATION_MESSAGE_FOUND_FOR_ID = "Notification message not found for this id : {0}";
    public static final String NO_TEAM_MEMBER_FOUND = "No team member found";
    public static final String NO_TEAM_MEMBER_FOUND_FOR_ID = "Team member not found for this id : {0}";

    public static final String ERROR_READ_TOKEN_EXCEPTION = "Cannot read token";
    public static final String START_DATE_MUST_BE_DEFINED_WHEN_STATUS_IS = "Start date must be defined when status is {0}";
    public static final String END_DATE_MUST_BE_DEFINED_WHEN_STATUS_IS = "End date must be defined when status is {0}";

    public static final String AVAILABILITY_CANT_BE_CREATED_OR_UPDATED_WHEN_LINKED_MISSION_STATUS_IS = "Availability can\'t be created or updated when linked mission status is {0}";
    public static final String VALIDATION_ERROR = "Validation error";
    public static final String MALFORMED_JSON_REQUEST = "Malformed JSON request";

    private ErrorConstant() {
    }

}
