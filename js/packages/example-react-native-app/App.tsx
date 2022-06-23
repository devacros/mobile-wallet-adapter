import React from 'react';
import {Button, SafeAreaView, ScrollView, StatusBar} from 'react-native';
import {withLocalWallet} from '@solana-mobile/mobile-wallet-adapter-protocol';

const App = () => {
  function handlePress() {
    withLocalWallet(wallet => {
      console.log(wallet);
    });
  }

  return (
    <SafeAreaView>
      <StatusBar barStyle="dark-content" />
      <ScrollView contentInsetAdjustmentBehavior="automatic">
        <Button onPress={handlePress} title="Connect" />
      </ScrollView>
    </SafeAreaView>
  );
};

export default App;
