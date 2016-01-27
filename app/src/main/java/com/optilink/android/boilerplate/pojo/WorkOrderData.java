package com.optilink.android.boilerplate.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by tanner.tan on 2016/1/21.
 */
public class WorkOrderData extends BaseResponse implements Parcelable {
    public WorkOrder work_order;
    public SendContact send_contact;
    public ReceiveContact receive_contact;
    public Cargo cargo;
    public List<CargoDetail> cargo_details;
    public List<Task> tasks;
    public List<Charge> work_order_charges;
    public List<WorkOrderException> work_order_exceptions;

    public WorkOrderData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.work_order, 0);
        dest.writeParcelable(this.send_contact, 0);
        dest.writeParcelable(this.receive_contact, 0);
        dest.writeParcelable(this.cargo, 0);
        dest.writeTypedList(cargo_details);
        dest.writeTypedList(tasks);
        dest.writeTypedList(work_order_charges);
        dest.writeTypedList(work_order_exceptions);
    }

    protected WorkOrderData(Parcel in) {
        this.work_order = in.readParcelable(WorkOrder.class.getClassLoader());
        this.send_contact = in.readParcelable(SendContact.class.getClassLoader());
        this.receive_contact = in.readParcelable(ReceiveContact.class.getClassLoader());
        this.cargo = in.readParcelable(Cargo.class.getClassLoader());
        this.cargo_details = in.createTypedArrayList(CargoDetail.CREATOR);
        this.tasks = in.createTypedArrayList(Task.CREATOR);
        this.work_order_charges = in.createTypedArrayList(Charge.CREATOR);
        this.work_order_exceptions = in.createTypedArrayList(WorkOrderException.CREATOR);
    }

    public static final Creator<WorkOrderData> CREATOR = new Creator<WorkOrderData>() {
        public WorkOrderData createFromParcel(Parcel source) {
            return new WorkOrderData(source);
        }

        public WorkOrderData[] newArray(int size) {
            return new WorkOrderData[size];
        }
    };

    @Override
    public String toString() {
        return "WorkOrderData{" +
                "work_order=" + work_order +
                ", send_contact=" + send_contact +
                ", receive_contact=" + receive_contact +
                ", cargo=" + cargo +
                ", cargo_details=" + cargo_details +
                ", tasks=" + tasks +
                ", work_order_charges=" + work_order_charges +
                ", work_order_exceptions=" + work_order_exceptions +
                '}';
    }

    public static class WorkOrder implements Parcelable {
        public String driver_uid;
        public long id;
        public String work_order_number;
        public long sales_order_id;
        public int work_order_type_id;
        public long driver_id;
        public String driver_name;
        public String driver_mobile;
        public int vehicle_type_id;
        public long vehicle_id;
        public String plate_no;
        public int percent;
        public String status;
        public String note;
        public String attachment;

        public WorkOrder() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.driver_uid);
            dest.writeLong(this.id);
            dest.writeString(this.work_order_number);
            dest.writeLong(this.sales_order_id);
            dest.writeInt(this.work_order_type_id);
            dest.writeLong(this.driver_id);
            dest.writeString(this.driver_name);
            dest.writeString(this.driver_mobile);
            dest.writeInt(this.vehicle_type_id);
            dest.writeLong(this.vehicle_id);
            dest.writeString(this.plate_no);
            dest.writeInt(this.percent);
            dest.writeString(this.status);
            dest.writeString(this.note);
            dest.writeString(this.attachment);
        }

        protected WorkOrder(Parcel in) {
            this.driver_uid = in.readString();
            this.id = in.readLong();
            this.work_order_number = in.readString();
            this.sales_order_id = in.readLong();
            this.work_order_type_id = in.readInt();
            this.driver_id = in.readLong();
            this.driver_name = in.readString();
            this.driver_mobile = in.readString();
            this.vehicle_type_id = in.readInt();
            this.vehicle_id = in.readLong();
            this.plate_no = in.readString();
            this.percent = in.readInt();
            this.status = in.readString();
            this.note = in.readString();
            this.attachment = in.readString();
        }

        public static final Creator<WorkOrder> CREATOR = new Creator<WorkOrder>() {
            public WorkOrder createFromParcel(Parcel source) {
                return new WorkOrder(source);
            }

            public WorkOrder[] newArray(int size) {
                return new WorkOrder[size];
            }
        };

        @Override
        public String toString() {
            return "WorkOrder{" +
                    "driver_uid='" + driver_uid + '\'' +
                    ", id=" + id +
                    ", work_order_number='" + work_order_number + '\'' +
                    ", sales_order_id=" + sales_order_id +
                    ", work_order_type_id=" + work_order_type_id +
                    ", driver_id=" + driver_id +
                    ", driver_name='" + driver_name + '\'' +
                    ", driver_mobile='" + driver_mobile + '\'' +
                    ", vehicle_type_id=" + vehicle_type_id +
                    ", vehicle_id=" + vehicle_id +
                    ", plate_no='" + plate_no + '\'' +
                    ", percent=" + percent +
                    ", status='" + status + '\'' +
                    ", note='" + note + '\'' +
                    ", attachment='" + attachment + '\'' +
                    '}';
        }
    }

    public static class Task implements Parcelable {
        public long work_order_id;
        public long id;
        public String name;
        public int priority;
        public boolean required;
        public int percent;
        public String action;
        public boolean kpi_completed;
        public String status;
        public String pre_task_names;
        public String description;
        public String longitude;
        public String latitude;
        public String updated_at;
        public String attachment;
        public String expected_time;
        public String execute_second;

        public Task() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.work_order_id);
            dest.writeLong(this.id);
            dest.writeString(this.name);
            dest.writeInt(this.priority);
            dest.writeByte(required ? (byte) 1 : (byte) 0);
            dest.writeInt(this.percent);
            dest.writeString(this.action);
            dest.writeByte(kpi_completed ? (byte) 1 : (byte) 0);
            dest.writeString(this.status);
            dest.writeString(this.pre_task_names);
            dest.writeString(this.description);
            dest.writeString(this.longitude);
            dest.writeString(this.latitude);
            dest.writeString(this.updated_at);
            dest.writeString(this.attachment);
            dest.writeString(this.expected_time);
            dest.writeString(this.execute_second);
        }

        protected Task(Parcel in) {
            this.work_order_id = in.readLong();
            this.id = in.readLong();
            this.name = in.readString();
            this.priority = in.readInt();
            this.required = in.readByte() != 0;
            this.percent = in.readInt();
            this.action = in.readString();
            this.kpi_completed = in.readByte() != 0;
            this.status = in.readString();
            this.pre_task_names = in.readString();
            this.description = in.readString();
            this.longitude = in.readString();
            this.latitude = in.readString();
            this.updated_at = in.readString();
            this.attachment = in.readString();
            this.expected_time = in.readString();
            this.execute_second = in.readString();
        }

        public static final Creator<Task> CREATOR = new Creator<Task>() {
            public Task createFromParcel(Parcel source) {
                return new Task(source);
            }

            public Task[] newArray(int size) {
                return new Task[size];
            }
        };

        @Override
        public String toString() {
            return "Task{" +
                    "work_order_id=" + work_order_id +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", priority=" + priority +
                    ", required=" + required +
                    ", percent=" + percent +
                    ", action='" + action + '\'' +
                    ", kpi_completed=" + kpi_completed +
                    ", status='" + status + '\'' +
                    ", pre_task_names='" + pre_task_names + '\'' +
                    ", description='" + description + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    ", attachment='" + attachment + '\'' +
                    ", expected_time='" + expected_time + '\'' +
                    ", execute_second='" + execute_second + '\'' +
                    '}';
        }
    }

    public static class SendContact implements Parcelable {
        public long sales_order_id;
        public long id;
        public String contact_type;
        public String name;
        public String mobile;
        public String telephone;
        public String order_address;
        public String enquiry_address;
        public String expected_time;
        public String note;

        public SendContact() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.sales_order_id);
            dest.writeLong(this.id);
            dest.writeString(this.contact_type);
            dest.writeString(this.name);
            dest.writeString(this.mobile);
            dest.writeString(this.telephone);
            dest.writeString(this.order_address);
            dest.writeString(this.enquiry_address);
            dest.writeString(this.expected_time);
            dest.writeString(this.note);
        }

        protected SendContact(Parcel in) {
            this.sales_order_id = in.readLong();
            this.id = in.readLong();
            this.contact_type = in.readString();
            this.name = in.readString();
            this.mobile = in.readString();
            this.telephone = in.readString();
            this.order_address = in.readString();
            this.enquiry_address = in.readString();
            this.expected_time = in.readString();
            this.note = in.readString();
        }

        public static final Creator<SendContact> CREATOR = new Creator<SendContact>() {
            public SendContact createFromParcel(Parcel source) {
                return new SendContact(source);
            }

            public SendContact[] newArray(int size) {
                return new SendContact[size];
            }
        };

        @Override
        public String toString() {
            return "SendContact{" +
                    "sales_order_id=" + sales_order_id +
                    ", id=" + id +
                    ", contact_type='" + contact_type + '\'' +
                    ", name='" + name + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", telephone='" + telephone + '\'' +
                    ", order_address='" + order_address + '\'' +
                    ", enquiry_address='" + enquiry_address + '\'' +
                    ", expected_time='" + expected_time + '\'' +
                    ", note='" + note + '\'' +
                    '}';
        }
    }

    public static class ReceiveContact implements Parcelable {
        public long sales_order_id;
        public long id;
        public String contact_type;
        public String name;
        public String mobile;
        public String telephone;
        public String order_address;
        public String enquiry_address;
        public String expected_time;

        public ReceiveContact() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.sales_order_id);
            dest.writeLong(this.id);
            dest.writeString(this.contact_type);
            dest.writeString(this.name);
            dest.writeString(this.mobile);
            dest.writeString(this.telephone);
            dest.writeString(this.order_address);
            dest.writeString(this.enquiry_address);
            dest.writeString(this.expected_time);
        }

        protected ReceiveContact(Parcel in) {
            this.sales_order_id = in.readLong();
            this.id = in.readLong();
            this.contact_type = in.readString();
            this.name = in.readString();
            this.mobile = in.readString();
            this.telephone = in.readString();
            this.order_address = in.readString();
            this.enquiry_address = in.readString();
            this.expected_time = in.readString();
        }

        public static final Creator<ReceiveContact> CREATOR = new Creator<ReceiveContact>() {
            public ReceiveContact createFromParcel(Parcel source) {
                return new ReceiveContact(source);
            }

            public ReceiveContact[] newArray(int size) {
                return new ReceiveContact[size];
            }
        };

        @Override
        public String toString() {
            return "ReceiveContact{" +
                    "sales_order_id=" + sales_order_id +
                    ", id=" + id +
                    ", contact_type='" + contact_type + '\'' +
                    ", name='" + name + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", telephone='" + telephone + '\'' +
                    ", order_address='" + order_address + '\'' +
                    ", enquiry_address='" + enquiry_address + '\'' +
                    ", expected_time='" + expected_time + '\'' +
                    '}';
        }
    }

    public static class Cargo implements Parcelable {
        public long sales_order_id;
        public long id;
        public String name;
        public int count;
        public String volume;
        public String weight;
        public String note;

        public Cargo() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.sales_order_id);
            dest.writeLong(this.id);
            dest.writeString(this.name);
            dest.writeInt(this.count);
            dest.writeString(this.volume);
            dest.writeString(this.weight);
            dest.writeString(this.note);
        }

        protected Cargo(Parcel in) {
            this.sales_order_id = in.readLong();
            this.id = in.readLong();
            this.name = in.readString();
            this.count = in.readInt();
            this.volume = in.readString();
            this.weight = in.readString();
            this.note = in.readString();
        }

        public static final Creator<Cargo> CREATOR = new Creator<Cargo>() {
            public Cargo createFromParcel(Parcel source) {
                return new Cargo(source);
            }

            public Cargo[] newArray(int size) {
                return new Cargo[size];
            }
        };

        @Override
        public String toString() {
            return "Cargo{" +
                    "sales_order_id=" + sales_order_id +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", count=" + count +
                    ", volume='" + volume + '\'' +
                    ", weight='" + weight + '\'' +
                    ", note='" + note + '\'' +
                    '}';
        }
    }

    public static class CargoDetail implements Parcelable {
        public long cargo_id;
        public long id;
        public String name;
        @com.google.gson.annotations.SerializedName("package")
        public String package_name;
        public int count;
        public int length;
        public int width;
        public int height;
        public String weight;
        public String note;

        public CargoDetail() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.cargo_id);
            dest.writeLong(this.id);
            dest.writeString(this.name);
            dest.writeString(this.package_name);
            dest.writeInt(this.count);
            dest.writeInt(this.length);
            dest.writeInt(this.width);
            dest.writeInt(this.height);
            dest.writeString(this.weight);
            dest.writeString(this.note);
        }

        protected CargoDetail(Parcel in) {
            this.cargo_id = in.readLong();
            this.id = in.readLong();
            this.name = in.readString();
            this.package_name = in.readString();
            this.count = in.readInt();
            this.length = in.readInt();
            this.width = in.readInt();
            this.height = in.readInt();
            this.weight = in.readString();
            this.note = in.readString();
        }

        public static final Creator<CargoDetail> CREATOR = new Creator<CargoDetail>() {
            public CargoDetail createFromParcel(Parcel source) {
                return new CargoDetail(source);
            }

            public CargoDetail[] newArray(int size) {
                return new CargoDetail[size];
            }
        };

        @Override
        public String toString() {
            return "CargoDetail{" +
                    "cargo_id=" + cargo_id +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", package_name='" + package_name + '\'' +
                    ", count=" + count +
                    ", length=" + length +
                    ", width=" + width +
                    ", height=" + height +
                    ", weight='" + weight + '\'' +
                    ", note='" + note + '\'' +
                    '}';
        }
    }

    public static class Charge implements Parcelable {
        public long work_order_id;
        public long id;
        public String charge_type_id;
        public String charge;
        public boolean enabled;
        public String note;
        public String attachment;
        public String updated_at;
        public ChargeType charge_type;


        public static class ChargeType implements Parcelable {
            public int id;
            public String category;
            public String name;
            public boolean enabled;

            public ChargeType() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.category);
                dest.writeString(this.name);
                dest.writeByte(enabled ? (byte) 1 : (byte) 0);
            }

            protected ChargeType(Parcel in) {
                this.id = in.readInt();
                this.category = in.readString();
                this.name = in.readString();
                this.enabled = in.readByte() != 0;
            }

            public static final Creator<ChargeType> CREATOR = new Creator<ChargeType>() {
                public ChargeType createFromParcel(Parcel source) {
                    return new ChargeType(source);
                }

                public ChargeType[] newArray(int size) {
                    return new ChargeType[size];
                }
            };

            @Override
            public String toString() {
                return "ChargeType{" +
                        "id=" + id +
                        ", category='" + category + '\'' +
                        ", name='" + name + '\'' +
                        ", enabled=" + enabled +
                        '}';
            }
        }

        public Charge() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.work_order_id);
            dest.writeLong(this.id);
            dest.writeString(this.charge_type_id);
            dest.writeString(this.charge);
            dest.writeByte(enabled ? (byte) 1 : (byte) 0);
            dest.writeString(this.note);
            dest.writeString(this.attachment);
            dest.writeString(this.updated_at);
            dest.writeParcelable(this.charge_type, 0);
        }

        protected Charge(Parcel in) {
            this.work_order_id = in.readLong();
            this.id = in.readLong();
            this.charge_type_id = in.readString();
            this.charge = in.readString();
            this.enabled = in.readByte() != 0;
            this.note = in.readString();
            this.attachment = in.readString();
            this.updated_at = in.readString();
            this.charge_type = in.readParcelable(ChargeType.class.getClassLoader());
        }

        public static final Creator<Charge> CREATOR = new Creator<Charge>() {
            public Charge createFromParcel(Parcel source) {
                return new Charge(source);
            }

            public Charge[] newArray(int size) {
                return new Charge[size];
            }
        };

        @Override
        public String toString() {
            return "Charge{" +
                    "work_order_id=" + work_order_id +
                    ", id=" + id +
                    ", charge_type_id='" + charge_type_id + '\'' +
                    ", charge='" + charge + '\'' +
                    ", enabled=" + enabled +
                    ", note='" + note + '\'' +
                    ", attachment='" + attachment + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    ", charge_type=" + charge_type +
                    '}';
        }
    }

    public static class WorkOrderException implements Parcelable {
        public long work_order_id;
        public long id;
        public String category;
        public String note;
        public String longitude;
        public String latitude;
        public String event_time;
        public String attachment;

        public WorkOrderException() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.work_order_id);
            dest.writeLong(this.id);
            dest.writeString(this.category);
            dest.writeString(this.note);
            dest.writeString(this.longitude);
            dest.writeString(this.latitude);
            dest.writeString(this.event_time);
            dest.writeString(this.attachment);
        }

        protected WorkOrderException(Parcel in) {
            this.work_order_id = in.readLong();
            this.id = in.readLong();
            this.category = in.readString();
            this.note = in.readString();
            this.longitude = in.readString();
            this.latitude = in.readString();
            this.event_time = in.readString();
            this.attachment = in.readString();
        }

        public static final Creator<WorkOrderException> CREATOR = new Creator<WorkOrderException>() {
            public WorkOrderException createFromParcel(Parcel source) {
                return new WorkOrderException(source);
            }

            public WorkOrderException[] newArray(int size) {
                return new WorkOrderException[size];
            }
        };

        @Override
        public String toString() {
            return "WorkOrderException{" +
                    "work_order_id=" + work_order_id +
                    ", id=" + id +
                    ", category='" + category + '\'' +
                    ", note='" + note + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", event_time='" + event_time + '\'' +
                    ", attachment='" + attachment + '\'' +
                    '}';
        }
    }
}
