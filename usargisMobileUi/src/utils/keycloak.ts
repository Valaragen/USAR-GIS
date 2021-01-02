import { RNKeycloak } from '@react-keycloak/native';

const keycloak = new RNKeycloak({
    url: 'http://192.168.1.12:8180/auth',
    realm: 'USAR-GIS',
    clientId: 'mobile-ui',
});

export default keycloak;