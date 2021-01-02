import React, { useState } from 'react';
import { View, Text, Pressable, FlatList, Button } from 'react-native';
import { useKeycloak } from '@react-keycloak/native';
import { KeycloakTokenParsed } from '@react-keycloak/keycloak-ts';
import { getAllMissions } from './API/usargisApi';
import MissionItem from './components/MissionItem'

type ParsedToken = KeycloakTokenParsed & {
    email?: string;
    preferred_username?: string;
    given_name?: string;
    family_name?: string;
}

const AppNavigation = () => {
    const { keycloak } = useKeycloak();
    const [missionsState, setMissionsState] = useState([]);
    const parsedToken: ParsedToken | undefined = keycloak?.tokenParsed;

    function _loadMissions() {
        console.log("loadMission")
        getAllMissions().then(data => console.log(data));
    }

    return (
        <View>
            {/* <Text>Bienvenue dans l'app USAR-GIS {parsedToken?.preferred_username}</Text>
            <Text>Cliquez ici pour vous déconnecter</Text>
            <Button onPress={() => keycloak?.logout()} title="Logout" /> */}

            <FlatList
                data={missionsState}
                keyExtractor={(item) => item.id.toString()}
                renderItem={({ item }) => <Text>{item.title}</Text>}
            />
            <Pressable onPress={() => _loadMissions()}>
                <Text>load</Text>
            </Pressable>
            <Button title="logout" onPress={() => keycloak?.logout()}></Button>
        </View>
    )
}

export default AppNavigation;