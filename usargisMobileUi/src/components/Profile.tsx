import React from 'react';
import { View } from 'react-native';
import { Card, Title, Paragraph, Button } from 'react-native-paper';


export default function Profile() {
    return (
        <View style={{ flex: 1 }}>
            <Card>
                <Card.Content>
                    <Title>Hello</Title>
                    <Paragraph>Pepegino</Paragraph>
                    <Button mode='contained'>Presserino pls</Button>
                </Card.Content>
            </Card>
        </View>
    );
}