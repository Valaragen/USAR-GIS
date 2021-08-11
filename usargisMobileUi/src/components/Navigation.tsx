import React from 'react';
import { useKeycloak } from '@react-keycloak/native';

import AppNavigation from 'components/AppNavigation'
import Login from 'components/Login';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

const Stack = createNativeStackNavigator();


const Navigation = () => {
    const { keycloak } = useKeycloak();

    return (
            <Stack.Navigator>
                {!keycloak?.authenticated ? (
                    <Stack.Screen name="Connexion" component={Login} />
                ) : (
                    <Stack.Screen name="Missions" component={AppNavigation} />
                )}
            </Stack.Navigator>
    );
}

export default Navigation;