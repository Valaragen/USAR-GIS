import * as React from 'react';
import { useKeycloak } from '@react-keycloak/native';

import AppNavigation from './AppNavigation'
import Login from './Login';

const Navigation = () => {
    const { keycloak, initialized } = useKeycloak();

    return (
        <>
            {!keycloak?.authenticated ? (
                <Login />
            ) : (
                <AppNavigation />
            )}
            
        </>
    );
}

export default Navigation;