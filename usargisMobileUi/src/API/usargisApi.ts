import { GATEWAY_URL, API_PATH } from 'utils/const';
import keycloak from 'utils/keycloak';
import { Mission } from 'utils/types/apiTypes';
import HttpError from 'api/HttpError';

const path = GATEWAY_URL + API_PATH;

async function _handleGetRequest(url:string) {
    try {
        const response = await fetch(url, {
            method: 'GET',
            headers: new Headers({
                'Authorization': 'Bearer ' + keycloak.token
            })
        })
        if (response.ok) {
            return await response.json();
        } else {
            throw new HttpError(response.status);
        }
    } catch (error) {
        throw new Error("Bad Response from server : " + error);
    }
}

export const searchForMissions = async (pagesize:number, page:number): Promise<Mission[]> => {
    const url = `${path}/missions?pageSize=${pagesize}&pageNo=${page}`;
    return _handleGetRequest(url);
}

export const getMissionById = async (missionId:number): Promise<Mission> => {
    const url = `${path}/missions/${missionId}`;
    return _handleGetRequest(url);
}