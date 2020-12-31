import * as React from 'react';
import { View, Text, Button } from 'react-native';
import { useKeycloak } from '@react-keycloak/native';


const AppNavigation = () => {
    const { keycloak, initialized } = useKeycloak();

    return (
        <View>
            <Text>Bienvenue dans l'app USAR-GIS</Text>
            <Text>Cliquez ici pour vous déconnecter</Text>
            <Button onPress={() => keycloak?.logout()} title="Logout"/>
        </View>
    )
}

export default AppNavigation;