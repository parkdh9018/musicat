/* eslint-disable */
import * as THREE from 'three';
import { GLTF } from 'three/examples/jsm/loaders/GLTFLoader';
import { BackgroundStructure } from './BackgroundStructure';
import { Objects } from './Objects';
import { ExtraCat } from './ExtraCat';


interface ExtendedGLTF extends GLTF {
  nodes: any;
}

export const Background = ({...props}) => {

  const extraCat_position = new THREE.Vector3(2.6, 0, 1)

  return (
    <>
      <Objects num={1}/>
      <BackgroundStructure num={1}/>
      <ExtraCat scale={0.5} position={extraCat_position}/>
    </>
  );
};