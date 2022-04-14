const crypto = require('crypto');

const algorithm = 'camellia-192-ecb';         // algorithm

const iv = crypto.randomBytes(0);        // initialization vector

// const key = crypto.randomBytes(24);       // encryption / decryption key
const key = Buffer.from('123456789012345678901234', 'utf-8');

const cipher = crypto.createCipheriv(algorithm, key, iv);
const decipher = crypto.createDecipheriv(algorithm, key, iv);

/**
 *
 * @param {string} data
 * @returns {string}
 */
function encryptData(data) {
  let encrypted = cipher.update(data, 'utf-8', 'hex');
  encrypted += cipher.final('hex');

  return encrypted;
}

/**
 *
 * @param {string} data
 * @returns {string}
 */
function decryptData(data) {
  let decrypted = decipher.update(data, 'hex', 'utf-8');
  decrypted += decipher.final('utf-8');

  return decrypted;
}

module.exports = {
  encryptData,
  decryptData,
}
