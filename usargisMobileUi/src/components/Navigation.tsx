import * as React from 'react';
import { useKeycloak } from '@react-keycloak/native';

import AppNavigation from 'components/AppNavigation'
import Login from 'components/Login';

const Navigation = () => {
    const { keycloak } = useKeycloak();

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