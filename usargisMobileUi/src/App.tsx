import * as React from 'react';
import { Provider } from 'react-redux';
import { RNKeycloak, ReactNativeKeycloakProvider} from '@react-keycloak/native';

import Store from './Store/configureStore';
import Navigation from './Navigation';

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
          <Navigation></Navigation>
        </Provider>
    </ReactNativeKeycloakProvider>
  );
}