import * as React from 'react';
import { Provider } from 'react-redux';
import keycloak from 'utils/keycloak'
import { ReactNativeKeycloakProvider } from '@react-keycloak/native';

import Store from './store/configureStore';
import { NavigationContainer } from '@react-navigation/native';
import Navigation from './components/Navigation';

export default function App() {

  return (
    <ReactNativeKeycloakProvider
      authClient={keycloak}
      initOptions={{ redirectUri: 'usargis://Homepage' }}
      onEvent={(event, error) => {
        console.log('onKeycloakEvent', event, error);
      }}>
        <Provider store={Store}>
          <NavigationContainer>
            <Navigation></Navigation>
          </NavigationContainer>
        </Provider>
    </ReactNativeKeycloakProvider>
  );
}