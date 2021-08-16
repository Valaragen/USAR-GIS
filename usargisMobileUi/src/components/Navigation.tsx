import React, { useState, useEffect } from 'react';
import { useKeycloak } from '@react-keycloak/native';

import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { createMaterialBottomTabNavigator } from '@react-navigation/material-bottom-tabs';
import { RootStackParamList } from 'utils/types/navigatorTypes';
import Login from 'components/Login';
import MissionDetails from './MissionDetails';
import MissionList from 'components/MissionList';
import Profile from 'components/Profile';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import { Snackbar } from 'react-native-paper';

const Stack = createNativeStackNavigator<RootStackParamList>();
const Tab = createMaterialBottomTabNavigator<RootStackParamList>();


function Navigation() {
    const { keycloak } = useKeycloak();

    return (
            <Stack.Navigator>
                {!keycloak?.authenticated ? (
                    <Stack.Screen name="Login" component={Login} options={{ headerShown: false }} />
                ) : (
                    <>
                        <Stack.Screen name="Home" component={HomeTabs} options={{ headerShown: false }} />
                        <Stack.Screen name="MissionDetails" component={MissionDetails} options={{ title: 'Détails' }} />
                    </>
                )}
            </Stack.Navigator>
    );
}

function HomeTabs() {
    return (
        <Tab.Navigator
            initialRouteName="MissionList"
        // activeColor="#f0edf6"
        // inactiveColor="#3e2465"
        // barStyle={{ backgroundColor: '#694fad' }}
        >
            <Tab.Screen
                name="MissionList"
                component={MissionList}
                options={{
                    tabBarLabel: 'Missions',
                    tabBarColor: '#694fad',
                    tabBarIcon: ({ color }) => (
                        <MaterialCommunityIcons name="briefcase" color={color} size={26} />
                    ),
                }}
            />
            <Tab.Screen
                name="Profile"
                component={Profile}
                options={{
                    tabBarLabel: 'Profil',
                    tabBarColor: 'green',
                    tabBarIcon: ({ color }) => (
                        <MaterialCommunityIcons name="account" color={color} size={26} />
                    ),
                }}
            />
        </Tab.Navigator>
    );
}

export default Navigation;