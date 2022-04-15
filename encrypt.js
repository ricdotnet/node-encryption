const crypto = require('crypto');
const fs = require('fs');

const algorithm = 'aes-128-ecb';                // algorithm used for encryption

// const iv = crypto.randomBytes(32).toString('hex');          // initialization vector
const iv = Buffer.from('1234567890123456', 'hex');

const key = fs.readFileSync('key.txt', 'hex');

const cipher = crypto.createCipheriv(algorithm, key, Buffer.alloc(0));
const decipher = crypto.createDecipheriv(algorithm, key, Buffer.alloc(0));

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
};
