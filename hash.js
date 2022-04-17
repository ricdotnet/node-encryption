const crypto = require('crypto');

const hash = crypto.createHash('sha256');

/**
 *
 * @param password
 * @returns {string}
 */
function hashPassword(password) {
  hash.update(password);
  return hash.digest('hex');
}

/**
 *
 * @param password
 * @param hash
 * @returns {boolean}
 */
function verifyPassword(password, hash) {
  return hashPassword(password) === hash;
}

const args = process.argv.slice(2);

if (args[0] === 'hash') {
  console.log(hashPassword(args[1]));
} else if (args[0] === 'verify') {
  console.log(verifyPassword(args[1], args[2]));
}
