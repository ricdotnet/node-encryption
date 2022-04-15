const args = process.argv.slice(2);
const fs = require('fs');
const crypto = require('crypto');

if (args.length === 1 && args[0] === '--make:key') {
  fs.writeFileSync('key.txt', crypto.randomBytes(4).toString('hex'));
  console.log('key.txt created with a 16-byte random hex key');
  process.exit(1);
}

const { encryptData, decryptData } = require('./encrypt');

if (args.length === 1 && args[0] === '--encrypt') {
  console.log('Please enter a file name to encrypt its data.');
  console.log('node index.js --encrypt <file>');
  process.exit(1);
}

if (args.length === 2 && args[0] === '--encrypt') {
  const data = fs.readFileSync(args[1], 'utf8');
  fs.writeFileSync(`${args[1]}.encrypted`, encryptData(data), 'utf8');
}

if (args.length === 1 && args[0] === '--decrypt') {
  console.log('Please enter a file name to decrypt its data.');
  console.log('node index.js --decrypt <file>');
  process.exit(1);
}

if (args.length === 2 && args[0] === '--decrypt') {
  const data = fs.readFileSync(args[1], 'utf-8');
  fs.writeFileSync(`data.txt.decrypted`, decryptData(data), 'utf-8');
}
