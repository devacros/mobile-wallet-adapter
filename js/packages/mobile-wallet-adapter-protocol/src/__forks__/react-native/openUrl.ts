import { Linking } from 'react-native';

export default async function openUrl(url: URL) {
    await Linking.openURL(url.toString());
}
