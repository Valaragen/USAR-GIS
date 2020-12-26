import * as React from 'react';

import { Provider } from 'react-redux';
import Store from './Store/configureStore';

import { RNKeycloak, ReactNativeKeycloakProvider, useKeycloak } from '@react-keycloak/native';
import Login from './Login';

export default function App() {

  const keycloakAuth = new RNKeycloak({
    url: 'http://10.0.2.2:8180/auth',
    realm: 'USAR-GIS',
    clientId: 'mobile-ui',
  });

  return (
    <ReactNativeKeycloakProvider
      authClient={keycloakAuth}
      initOptions={{ redirectUri: 'usargis://Homepage' }}>
        <Provider store={Store}>
          <Login />
        </Provider>
    </ReactNativeKeycloakProvider>
  );
}