import React from 'react';
import { View, Text, Pressable, Image, StyleSheet, ImageBackground } from 'react-native';
import { useKeycloak } from '@react-keycloak/native';

const Login = () => {
  const { keycloak } = useKeycloak();

  return (
    <View style={styles.container}>
      <Image
        style={styles.image}
        source={require('./img/logo-usar-gis.png')}
      />
      <View>
        <Pressable style={({ pressed }) => [
          styles.button,
          pressed && styles.buttonPressed
        ]} onPress={() => keycloak?.login()}>
          <Text style={styles.buttonText}>Connexion</Text>
        </Pressable>
        <Pressable style={({ pressed }) => [
          styles.button,
          pressed && styles.buttonPressed,
          styles.marginTop
        ]} onPress={() => keycloak?.register()}>
          <Text style={styles.buttonText}>Inscription</Text>
        </Pressable>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'space-evenly'
  },
  image: {
    resizeMode: 'contain',
    height: '30%'
  },
  text: {
    fontSize: 22,
    textAlign: 'center'
  },
  button: {
    backgroundColor: '#e22013',
    color: '#fff',
    padding: 10,
    borderRadius: 6
  },
  buttonPressed: {
    backgroundColor: '#ff3d2f'
  },
  buttonText: {
    fontSize: 22,
    color: 'white'
  },
  marginTop: {
    marginTop: 20
  }
});

export default Login;