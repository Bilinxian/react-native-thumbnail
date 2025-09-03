
# react-native-thumbnail
Get thumbnail from local media. Currently, it only supports for video.

## Getting started

`npm install react-native-thumbnail --save` or `yarn add react-native-thumbnail`


## Usage
```javascript
import RNThumbnail from 'react-native-thumbnail';

RNThumbnail.get(filepath).then((path) => {
  console.log(path); // thumbnail path
})
```
