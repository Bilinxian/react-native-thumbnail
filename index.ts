import {NativeModules} from 'react-native';

const {RNThumbnail} = NativeModules;

const Thumbnail = {
  get(filepath: string): Promise<string> {
    return RNThumbnail.get(filepath)
  }
}

export default Thumbnail;
