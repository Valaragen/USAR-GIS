import React from 'react';
import { View, Text, Pressable, Image, StyleSheet } from 'react-native';
import { useKeycloak } from '@react-keycloak/native';
import { Button } from 'react-native-paper';

function Login() {
  const { keycloak } = useKeycloak();

  return (
    <View style={style.container}>
      <Image
        style={style.image}
        source={require('../img/logo-usar-gis.png')}
      />
      <View>
        <Button mode='contained' style={{marginBottom: 20}} contentStyle={{margin: 5}} onPress={() => keycloak?.login()}>
          <Text style={style.buttonText}>Connexion</Text>
        </Button>
        <Button mode='contained' contentStyle={{margin: 5}} onPress={() => keycloak?.register()}>
          <Text style={style.buttonText}>Inscription</Text>
        </Button>
      </View>
    </View>
  );
};

const style = StyleSheet.create({
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