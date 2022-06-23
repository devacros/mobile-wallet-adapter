import crypto from './crypto';

export default function toBase64(data: string): string {
    return crypto.toBase64(crypto.textToBytes(data));
}
