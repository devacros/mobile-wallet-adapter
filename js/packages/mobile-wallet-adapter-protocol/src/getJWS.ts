import arrayBufferToBase64String from './arrayBufferToBase64String';
import crypto from './crypto';
import toBase64 from './toBase64';

export default async function getJWS(payload: string, privateKey: CryptoKey) {
    const header = { alg: 'ES256' };
    const headerEncoded = toBase64(JSON.stringify(header));
    const payloadEncoded = toBase64(payload);
    const message = `${headerEncoded}.${payloadEncoded}`;
    const signatureBuffer = await crypto.subtle.sign(
        {
            name: 'ECDSA',
            hash: 'SHA-256',
        },
        privateKey,
        new TextEncoder().encode(message),
    );
    const signature = arrayBufferToBase64String(signatureBuffer);
    const jws = `${message}.${signature}`;
    return jws;
}
