import React from 'react';
import { View, Text, Button } from 'react-native';
import { useKeycloak } from '@react-keycloak/native';
import styles from './styles';

const Login = () => {
  const { keycloak } = useKeycloak();

  return (
    <View style={styles.container}>
      <Text>{`Connectez vous !`}</Text>
      <Button onPress={() => keycloak?.login()} title="Login" />
    </View>
  );
};

export default Login;