import { GATEWAY_URL, API_PATH } from '../utils/const';
import keycloak from '../utils/keycloak';

const path = GATEWAY_URL + API_PATH;

export const getAllMissions = async (): Promise<void | any[]> => {
    const url = `${path}/missions`;
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
            console.log("An error occured : " + response.status)
        }
    } catch (error) {
        return console.error(error);
    }
}