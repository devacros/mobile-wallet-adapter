import { AssociationPort, getRandomAssociationPort } from './associationPort';
import { SolanaMobileWalletAdapterWalletNotInstalledError } from './errors';
import getAssociateAndroidIntentURL from './getAssociateAndroidIntentURL';
import openUrl from './openUrl';

export async function startSession(
    associationPublicKey: CryptoKey,
    associationURLBase?: string,
): Promise<AssociationPort> {
    const randomAssociationPort = getRandomAssociationPort();
    const associationUrl = await getAssociateAndroidIntentURL(
        associationPublicKey,
        randomAssociationPort,
        associationURLBase,
    );
    try {
        openUrl(associationUrl);
    } catch (e) {
        throw new SolanaMobileWalletAdapterWalletNotInstalledError();
    }
    return randomAssociationPort;
}
