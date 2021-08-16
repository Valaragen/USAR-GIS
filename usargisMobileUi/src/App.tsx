import * as React from 'react';
import keycloak from 'utils/keycloak'
import { ReactNativeKeycloakProvider } from '@react-keycloak/native';
import { Provider as StoreProvider } from 'react-redux';
import Store from './store/configureStore';
import { Provider as PaperProvider } from 'react-native-paper'
import { NavigationContainer } from '@react-navigation/native';
import Navigation from './components/Navigation';
import PaperCustomTheme from 'styles/PaperCustomTheme';
import NavigationDefaultTheme from 'styles/NavigationCustomTheme';

export default function App() {

  return (
    <ReactNativeKeycloakProvider
      authClient={keycloak}
      initOptions={{ redirectUri: 'usargis://Homepage' }}
      onEvent={(event, error) => {
        console.log('onKeycloakEvent', event, error);
      }}>
      <StoreProvider store={Store}>
        <PaperProvider theme={PaperCustomTheme}>
          <NavigationContainer theme={NavigationDefaultTheme}>
            <Navigation></Navigation>
          </NavigationContainer>
        </PaperProvider>
      </StoreProvider>
    </ReactNativeKeycloakProvider>
  );
}