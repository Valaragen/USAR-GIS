import React from 'react';
import { useKeycloak } from '@react-keycloak/native';

import MissionList from 'components/MissionList'
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Login from 'components/Login';
import MissionDetails from './MissionDetails';
import { RootStackParamList } from 'utils/types/NavigatorTypes';

const Stack = createNativeStackNavigator<RootStackParamList>();


const Navigation = () => {
    const { keycloak } = useKeycloak();

    return (
            <Stack.Navigator>
                {!keycloak?.authenticated ? (
                    <Stack.Screen name="Login" component={Login} />
                ) : (
                    <>
                        <Stack.Screen name="MissionList" component={MissionList} />
                        <Stack.Screen name="MissionDetails" component={MissionDetails} />
                    </>
                )}
            </Stack.Navigator>
    );
}

export default Navigation;