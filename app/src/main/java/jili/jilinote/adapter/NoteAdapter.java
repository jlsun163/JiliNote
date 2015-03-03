package jili.jilinote.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jili.jilinote.MainActivity;
import jili.jilinote.R;
import jili.jilinote.entity.NoteInfo;

/**
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                            O\ = /O
 * //                        ____/`---'\____
 * //                      .   ' \\| |// `.
 * //                       / \\||| : |||// \
 * //                     / _||||| -:- |||||- \
 * //                       | | \\\ - /// | |
 * //                     | \_| ''\---/'' | |
 * //                      \ .-\__ `-` ___/-. /
 * //                   ___`. .' /--.--\ `. . __
 * //                ."" '< `.___\_<|>_/___.' >'"".
 * //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //                 \ \ `-. \_ __\ /__ _/ .-` / /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                            `=---='
 * //
 * //         .............................................
 * //                  佛祖保佑             永无BUG
 * //          佛曰:
 * //                  写字楼里写字间，写字间里程序员；
 * //                  程序人员写程序，又拿程序换酒钱。
 * //                  酒醒只在网上坐，酒醉还来网下眠；
 * //                  酒醉酒醒日复日，网上网下年复年。
 * //                  但愿老死电脑间，不愿鞠躬老板前；
 * //                  奔驰宝马贵者趣，公交自行程序员。
 * //                  别人笑我忒疯癫，我笑自己命太贱；
 * //                  不见满街漂亮妹，哪个归得程序员？
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    private List<NoteInfo> noteinfos;
    private int rowLayout;
    private MainActivity mAct;

    public NoteAdapter(List<NoteInfo> notes, int rowLayout, MainActivity act) {
        this.noteinfos = notes;
        this.rowLayout = rowLayout;
        this.mAct = act;
    }


    public void clearApplications() {
        int size = this.noteinfos.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                noteinfos.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void addApplications(List<NoteInfo> noteInfos) {
        this.noteinfos.addAll(noteInfos);
        this.notifyItemRangeInserted(0, noteInfos.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final NoteInfo noteInfo = noteinfos.get(i);
        viewHolder.name.setText(noteInfo.getName());
        viewHolder.image.setImageResource(R.drawable.ic_launcher);

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAct.animateActivity(appInfo, viewHolder.image);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return noteinfos == null ? 0 : noteinfos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.countryName);
            image = (ImageView) itemView.findViewById(R.id.countryImage);
        }

    }
}
