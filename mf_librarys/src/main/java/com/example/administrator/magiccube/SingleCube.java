package com.example.administrator.magiccube;

import android.content.Context;
import android.opengl.GLES20;
import android.os.SystemClock;

import com.example.administrator.magiccube.constant.Const;
import com.example.administrator.magiccube.util.BufferUtil;
import com.example.administrator.magiccube.util.MatrixUtil;
import com.example.administrator.magiccube.util.ShaderUtil;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class SingleCube {

    private int mProgramHandle;
    private int mColorHandle;
    private int mPositionHandle;
    private int mMVPMatrixHandle;
    private final int VERTEX_ELEMENT_SIZE = 3;
    private final int COLOR_ELEMENT_SIZE = 4;
    private final int VERTEX_COUNT = 8;
    private float r = 0.3f;

    public float[] vertexArray = {
            // Front face
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,

            // Right face
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,

            // Back face
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            // Left face
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,

            // Top face
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,

            // Bottom face
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
    };
    byte[] index = {
            0, 1, 2, 3, 2, 1,
            6, 5, 7, 4, 7, 5,
            1, 3, 5, 4, 3, 5,
            0, 2, 6, 7, 2, 6,
            2, 3, 7, 4, 7, 3,
            0, 1, 6, 5, 6, 1

    };
    float[] colors = {
            // Front face (red)
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            // Right face (green)
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            // Back face (blue)
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            // Left face (yellow)
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            // Top face (cyan)
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,

            // Bottom face (magenta)
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f
    };

    public SingleCube(Context glSurfaceView) {
        // TODO Auto-generated method stub

        String vertexShader = ShaderUtil.loadFromAssetsFile(Const.VERTEX_FILE_NAME, glSurfaceView.getResources());
        String fragmentShader = ShaderUtil.loadFromAssetsFile(Const.FRAGMENT_FILE_NAME, glSurfaceView.getResources());

       ShaderUtil.setaColorName(Const.COLOR_NAME);
        ShaderUtil.setaPositionName(Const.POSITION_NAME);
        mProgramHandle = ShaderUtil.createProgram(vertexShader, fragmentShader);
        GLES20.glUseProgram(mProgramHandle);
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, Const.MATRIX_NAME);
        mPositionHandle = GLES20.glGetAttribLocation(mProgramHandle, Const.POSITION_NAME);
        mColorHandle = GLES20.glGetAttribLocation(mProgramHandle, Const.COLOR_NAME);
    }

    public void draw() {
        // TODO Auto-generated method stub
        long time = SystemClock.uptimeMillis() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);
        GLES20.glUseProgram(mProgramHandle);
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, Const.MATRIX_NAME);
        mPositionHandle = GLES20.glGetAttribLocation(mProgramHandle, Const.POSITION_NAME);
        mColorHandle = GLES20.glGetAttribLocation(mProgramHandle, Const.COLOR_NAME);
        MatrixUtil.setIdentityM(0);
        MatrixUtil.translateM( 0, 0.0f, 0.0f, 0.0f);
        MatrixUtil.rotateM(0, 45f, 1.0f, 1.0f, 0.0f);
        MatrixUtil.scaleM(0.5f,0.5f,0.5f);
        GLES20.glVertexAttribPointer(mPositionHandle, VERTEX_ELEMENT_SIZE, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(vertexArray));
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glVertexAttribPointer(mColorHandle, COLOR_ELEMENT_SIZE, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(colors));
        GLES20.glEnableVertexAttribArray(mColorHandle);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, MatrixUtil.getFinalMatrix(), 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);


    }
}
