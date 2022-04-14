const fs = require('fs');
const { encryptData, decryptData } = require('./encrypt');

const args = process.argv.slice(2);

if (args.length === 1 && args[0] === '--encrypt') {
  console.log('Please enter a file name to encrypt its data.');
  console.log('node index.js --encrypt <file>');
  process.exit(0);
}

if (args.length === 2 && args[0] === '--encrypt') {
  const data = fs.readFileSync(args[1], 'utf8');
  fs.writeFileSync(`${args[1]}.encrypted`, encryptData(data), 'utf8');
}

if (args.length === 1 && args[0] === '--decrypt') {
  console.log('Please enter a file name to decrypt its data.');
  console.log('node index.js --decrypt <file>');
  process.exit(0);
}

if (args.length === 2 && args[0] === '--decrypt') {
  const data = fs.readFileSync(args[1], 'utf-8');
  fs.writeFileSync(`${args[1]}.decrypted`, decryptData(data), 'utf-8');
}
