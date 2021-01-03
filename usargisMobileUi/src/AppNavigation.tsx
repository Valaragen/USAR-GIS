import React, { useState } from 'react';
import { View, Text, Pressable, FlatList, Button } from 'react-native';
import { useKeycloak } from '@react-keycloak/native';
import { KeycloakTokenParsed } from '@react-keycloak/keycloak-ts';
import { getAllMissions } from './API/usargisApi';
import { MissionItem, Mission } from './components/MissionItem'

type ParsedToken = KeycloakTokenParsed & {
    email?: string;
    preferred_username?: string;
    given_name?: string;
    family_name?: string;
}

const AppNavigation = () => {
    const { keycloak } = useKeycloak();
    const [missionsState, setMissionsState] = useState<Mission[]>([]);
    const parsedToken: ParsedToken | undefined = keycloak?.tokenParsed;

    function _loadMissions() {
        getAllMissions().then(data => setMissionsState(data));
        console.log(missionsState);
    }

    return (
        <View>
            {/* <Text>Bienvenue dans l'app USAR-GIS {parsedToken?.preferred_username}</Text>
            <Text>Cliquez ici pour vous déconnecter</Text>
            <Button onPress={() => keycloak?.logout()} title="Logout" /> */}

            <FlatList
                data={missionsState}
                keyExtractor={(item) => item.id.toString()}
                renderItem={({ item }) => <Text>{item.name}</Text>}
            />
            <Pressable onPress={() => _loadMissions()}>
                <Text>load</Text>
            </Pressable>
            <Button title="logout" onPress={() => keycloak?.logout()}></Button>
        </View>
    )
}

export default AppNavigation;