import { GATEWAY_URL, API_PATH } from 'utils/const';
import keycloak from 'utils/keycloak';
import { Mission } from 'components/MissionItem';

const path = GATEWAY_URL + API_PATH;

function _handleErrors(response: Response) {
    if (!response.ok) {
        throw Error(response.statusText);
    }
    return response;
}

export const searchForMissions = async (pagesize:number, page:number): Promise<Mission[]> => {
    const url = `${path}/missions` + '?pageSize=' + pagesize + '&pageNo=' + page;
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
            console.log("error");
            throw new Error("Bad Response from server : " + response.status);
        }
    } catch (error) {
        throw new Error("Bad Response from server : " + error);
    }
}