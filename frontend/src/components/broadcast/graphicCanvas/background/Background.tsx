/* eslint-disable */
import * as THREE from 'three';
import { GLTF } from 'three/examples/jsm/loaders/GLTFLoader';
import { BackgroundStructure } from './BackgroundStructure';
import { Objects } from './Objects';
import { ExtraCat } from './ExtraCat';
import { GroupProps } from '@react-three/fiber';


interface propsType extends GroupProps {
  num : number;
}

export const Background = ({num, ...props} : propsType) => {

  const extraCat_position = new THREE.Vector3(2.6, 0, 1)
  const backgroundStructre_rotation = new THREE.Euler(0, 0, 0);

  return (
    <>
      <Objects num={num}/>
      <BackgroundStructure num={num} rotation={backgroundStructre_rotation}/>
      <ExtraCat scale={0.5} position={extraCat_position}/>
    </>
  );
};