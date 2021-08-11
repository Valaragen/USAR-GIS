import { KeycloakTokenParsed } from '@react-keycloak/keycloak-ts';

type ParsedToken = KeycloakTokenParsed & {
    email?: string;
    preferred_username?: string;
    given_name?: string;
    family_name?: string;
}

export interface Mission {
    id: number;
    name: string;
    status: string;
    description?: string;
    startDate?: Date;
    endDate?: Date;
    plannedStartDate?: Date;
    expectedDurationInDays?: number;
    address?: string;
    latitude: number;
    longitude: number;
    creationDate?: Date;
    lastEditionDate?: Date;
    authorUsername: string;
}