import * as React from 'react';
import { Provider } from 'react-redux';
import keycloak from 'utils/keycloak'
import { ReactNativeKeycloakProvider } from '@react-keycloak/native';

import Store from './store/configureStore';
import Navigation from './components/Navigation';

export default function App() {

  return (
    <ReactNativeKeycloakProvider
      authClient={keycloak}
      initOptions={{ redirectUri: 'usargis://Homepage' }}>
        <Provider store={Store}>
          <Navigation></Navigation>
        </Provider>
    </ReactNativeKeycloakProvider>
  );
}